package embasa.persistence.maindb.repository.impl;

import embasa.i18n.LanguageHolder;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.model.WfTransitionTrigger;
import embasa.persistence.maindb.repository.WfTransitionTriggerRepository;
import embasa.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/** Реалізація репозиторію тригера переходу статуса workflow. */
public class WfTransitionTriggerRepositoryImpl implements WfTransitionTriggerRepository {

    /** Шаблон jdbc. */
    private JdbcTemplate jdbcTemplate;

    /** Ім'я таблиці сутностей. */
    private static String TABLE_NAME = "wf_transition_triggers";

    /** Скрипт отримання всіх сутностей пов'язаних з переходом. */
    private static String FIND_BY_TRANSITION = String.format(buildFindByTransitionIdSql(), TABLE_NAME);

    /** Скрипт додавання запису. */
    private static String INSERT = String.format(
            "INSERT INTO %s(transition_id, trigger_id, params) VALUES (?, ?, CAST(? AS JSON))", TABLE_NAME);

    /** Утримувач мов. */
    private LanguageHolder languageHolder;

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

    /**
     * Отримати скрипт пошуку всіх повязаних з переходом тригерів
     * @return скрипт пошуку всіх повязаних з переходом тригерів
     */
    private static String buildFindByTransitionIdSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.*, tt.params, tt.transition_id,");
        sql.append(" mv1.value AS name_value, mv1.lang AS name_lang,");
        sql.append(" mv2.value AS descr_value, mv2.lang AS descr_lang");
        sql.append(" FROM %s tt");
        sql.append(" INNER JOIN triggers t ON t.id = tt.trigger_id");
        sql.append(" INNER JOIN msg_langs l ON TRUE");
        sql.append(" LEFT JOIN msg_values mv1 ON mv1.const = t.name_const AND mv1.lang = l.code");
        sql.append(" LEFT JOIN msg_values mv2 ON mv2.const = t.descr_const AND mv2.lang = l.code");
        sql.append(" WHERE tt.transition_id = ?");
        sql.append(" ORDER BY t.id");
        return sql.toString();
    }

    @Override
    public List<WfTransitionTrigger> findByTransition(Long id) {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(FIND_BY_TRANSITION, id);
        ArrayList<WfTransitionTrigger> result = new ArrayList<> ();
        if (!srs.isAfterLast() && srs.next()) {
            while (!srs.isAfterLast()) {
                WfTransitionTrigger wfTrigger = new WfTransitionTrigger();
                wfTrigger.setTransitionId(srs.getLong("transition_id"));
                wfTrigger.setParams(srs.getString("params"));

                Trigger trigger = MapperUtil.mapTrigger(srs, languageHolder);
                wfTrigger.setTrigger(trigger);
                result.add(wfTrigger);
            }
        }
        return result;
    }

    @Override
    public void save(WfTransitionTrigger p) {
        jdbcTemplate.update(INSERT, new Object[] { p.getTransitionId(), p.getTrigger().getId(), p.getParams() },
                new int[] { Types.BIGINT, Types.BIGINT, Types.VARCHAR });
    }
}
