package embasa.persistence.securedb.repository.impl;

import embasa.enums.DataBase;
import embasa.i18n.LanguageHolder;
import embasa.persistence.securedb.model.Acsk;
import embasa.persistence.securedb.repository.AcskRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.HashMap;
import java.util.Map;

/** Реалізація репозиторія АЦСК. */
public class AcskRepositoryImpl extends SecureDBJdbcRepositoryLocalizableImpl<Acsk, Long> implements AcskRepository {

    /** Ім'я таблиці сутностей. */
    private static final String TABLE_NAME = "cert_centers";

    /** Sql-запит вибірки всіх записів. */
    private static final String FIND_ALL = builFindAllSql();

    /** Sql-запит вибірки запису за ідентифікатором. */
    private static final String FIND_BY_ID = FIND_ALL + " WHERE cc.id = ?";

    /** Запит оновлення запису в бд. */
    private static String UPDATE = String.format("UPDATE %s SET issuer_cns_const = ?, cmp_address = ?, cmp_port = ?" +
            "WHERE id = ?", TABLE_NAME);

    /** Об'єкт додавання сутності в бд. */
    private SimpleJdbcInsert jdbcInsert;

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Конструктор за замовчанням. */
    public AcskRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Autowired
    @Qualifier(value = "secureDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Override
    public void afterJdbcTemplateSet() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME)
                .withSchemaName(DataBase.SECURE_DB.getSchema());
        jdbcInsert.setGeneratedKeyName("id");;
    }

    /**
     * Отримати sql-запит вибірки всіх записів
     * @return sql-запит вибірки всіх записів
     */
    private static String builFindAllSql() {
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT");
        sql.append(" cc.id, cc.cmp_address, cc.cmp_port,");
        sql.append(" mv.const AS name_const, mv.value AS name_value, mv.lang AS name_lang");
        sql.append(" FROM cert_centers cc");
        sql.append(" LEFT JOIN msg_langs ml ON TRUE");
        sql.append(" LEFT JOIN msg_values mv ON mv.const = issuer_cns_const AND mv.lang = ml.code");
        return sql.toString();
    }

    @Override
    public Acsk mapEntity(SqlRowSet srs) {
        return MapperUtil.mapAcsk(srs, languageHolder);
    }

    @Override
    public void save(Acsk p) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmp_address", p.getCmpAddress());
        params.put("cmp_port", p.getCmpPort());
        params.put("issuer_cns_const", p.getNameCode());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        p.setId(id);
    }

    @Override
    public void update(Acsk p) {
        jdbcTemplate.update(UPDATE, p.getNameCode(), p.getCmpAddress(), p.getCmpPort(), p.getId());
    }

    @Override
    public String getFindByIdSql() { return FIND_BY_ID; }

    @Override
    public String getFindAllSql() {
        return FIND_ALL;
    }
}
