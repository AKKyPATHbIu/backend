package embasa.persistence.maindb.repository.impl;

import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.maindb.model.WfTransitionValidator;
import embasa.persistence.maindb.repository.WfTransitionValidatorRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/** Реалізація репозиторію валідатора переходу статуса workflow. */
public class WfTransitionValidatorRepositoryImpl implements WfTransitionValidatorRepository {

    /** Шаблон jdbc. */
    private JdbcTemplate jdbcTemplate;

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

    /** Ім'я таблиці сутностей. */
    private static String TABLE_NAME = "wf_transition_validators";

    /** Скрипт отримання всіх сутностей пов'язаних з переходом. */
    private static String FIND_BY_TRANSITION = String.format(buildFindByTransitionIdSql(), TABLE_NAME);

    /** Скрипт додавання запису. */
    private static String INSERT = String.format(
            "INSERT INTO %s(transition_id, validator_id, params) VALUES (?, ?, CAST(? AS JSON))", TABLE_NAME);

    /**
     * Отримати скрипт пошуку всіх повязаних з переходом тригерів
     * @return скрипт пошуку всіх повязаних з переходом тригерів
     */
    private static String buildFindByTransitionIdSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT v.*, tv.params, tv.transition_id,");
        sql.append(" mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(" FROM wf_transition_validators tv");
        sql.append(" INNER JOIN validators v ON v.id = tv.validator_id");
        sql.append(" INNER JOIN msg_langs l ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = v.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = v.descr_const AND mv2.lang = l.code");
        sql.append(" WHERE tv.transition_id = ?");
        sql.append(" ORDER BY v.id");
        return sql.toString();
    }

    @Autowired
    @Qualifier(value = "mainDBLanguageHolder")
    public void setLanguageHolder(LanguageHolder languageHolder) {
        this.languageHolder = languageHolder;
    }

    @Autowired
    @Qualifier(value = "mainDBJdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WfTransitionValidator> findByTransition(Long id) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_TRANSITION, id);
        ArrayList<WfTransitionValidator> result = new ArrayList<> ();
        if (!srs.isAfterLast() && srs.next()) {
            while (!srs.isAfterLast()) {
                WfTransitionValidator wfValidator = new WfTransitionValidator();
                wfValidator.setTransitionId(srs.getLong("transition_id"));
                wfValidator.setParams(srs.getString("params"));

                Validator validator = MapperUtil.mapValidator(srs, languageHolder);
                wfValidator.setValidator(validator);
                result.add(wfValidator);
            }
        }
        return result;
    }

    @Override
    public void save(WfTransitionValidator p) {
        jdbcTemplate.update(INSERT, new Object[] { p.getTransitionId(), p.getValidator().getId(), p.getParams() },
                new int[] { Types.BIGINT, Types.BIGINT, Types.VARCHAR });
    }
}
