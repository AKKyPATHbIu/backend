package embasa.persistence.securedb.repository.impl;

import embasa.persistence.securedb.model.User;
import embasa.persistence.securedb.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/** Реалізація репозиторія користувачів. */
public class UserRepositoryImpl extends SecureDBJdbcRepositoryInsertImpl<User, Long> implements UserRepository {

    /** Ім'я таблиці. */
    private static final String TABLE_NAME = "users";

    /** Sql-скрипт оновлення запису. */
    private static final String UPDATE = String.format("UPDATE %s SET login = ?, name = ?, email = ?" +
            ", account_enabled = ?, comment = ?, passwd = ?, passwd_expiration = ? WHERE id = ?", TABLE_NAME);

    /** Шаблон пошуку запису. */
    private static final String FIND_TEMPLATE = "SELECT * FROM %s WHERE %s = ? LIMIT 1";

    /** Пошук за логіном. */
    private static final String FIND_BY_LOGIN = String.format(FIND_TEMPLATE, TABLE_NAME, "login");

    /** Пошук за електронною адресою. */
    private static final String FIND_BY_EMAIL = String.format(FIND_TEMPLATE, TABLE_NAME, "email");

    /** Конструктор за замовчанням.  */
    public UserRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    public void save(User p) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", p.getUsername());
        params.put("name", p.getName());
        params.put("email", p.getEmail());
        params.put("account_enabled", p.getEnabled());
        params.put("comment", p.getComment());
        params.put("passwd", p.getPassword());
        params.put("passwd_expiration", p.getPasswordExpiration());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(User p) {
        jdbcTemplate.update(UPDATE, p.getUsername(), p.getName(), p.getEmail(), p.getEnabled(), p.getComment(),
                p.getPassword(), p.getPasswordExpiration(), p.getId());
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setEnabled(resultSet.getBoolean("account_enabled"));
        user.setComment(resultSet.getString("comment"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("passwd"));
        user.setPasswordExpiration(resultSet.getDate("passwd_expiration"));
        return user;
    }

    @Override
    public User findByLogin(String login) {
        return jdbcTemplate.queryForObject(FIND_BY_LOGIN, new Object[] { login },
                new int[] { Types.VARCHAR },  this);
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_BY_EMAIL, new Object[] { email },
                new int[] { Types.VARCHAR },  this);
    }
}
