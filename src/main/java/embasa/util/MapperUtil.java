package embasa.util;

import embasa.i18n.LanguageHolder;
import embasa.i18n.LocaleUtil;
import embasa.persistence.common.Descable;
import embasa.persistence.common.Nameable;
import embasa.persistence.common.model.CommonLocalized;
import embasa.persistence.maindb.model.Trigger;
import embasa.persistence.maindb.model.Validator;
import embasa.persistence.securedb.model.Acsk;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/** Допоміжна утиліта для мапінгу сутностей. */
public class MapperUtil {

    /**
     * Створити сутність {@link embasa.persistence.maindb.model.Trigger} з набору даних
     * @param srs набір даних
     * @param languageHolder утримувач мов
     * @return сутність {@link embasa.persistence.maindb.model.Trigger}
     */
    public static Trigger mapTrigger(SqlRowSet srs, LanguageHolder languageHolder) {
        Long id = srs.getLong("id");
        Trigger trigger = new Trigger();
        trigger.setId(id);
        trigger.setModuleId(srs.getLong("module_id"));
        trigger.setConf(srs.getString("conf"));
        trigger.getName().setCode(srs.getString("name_const"));
        trigger.getDescr().setCode(srs.getString("descr_const"));

        while (srs.getLong("id") == id) {
            addResources(srs, languageHolder, trigger);
            if (!srs.next()) {
                break;
            }

        }
        return trigger;
    }

    /**
     * Створити сутність {@link embasa.persistence.maindb.model.Validator} з набору даних
     * @param srs набір даних
     * @param languageHolder утримувач мов
     * @return сутність {@link embasa.persistence.maindb.model.Validator}
     */
    public static Validator mapValidator(SqlRowSet srs, LanguageHolder languageHolder) {
        Long id = srs.getLong("id");
        Long dataTypeId = srs.getLong("data_type_id");
        String nameConst = srs.getString("name_const");
        String descrConst = srs.getString("descr_const");
        String rule = srs.getString("rule");

        Validator validator = new Validator();
        validator.setNameCode(nameConst);
        validator.setDescCode(descrConst);
        validator.setId(id);
        validator.setDataTypeId(dataTypeId).setRule(rule);

        while(srs.getLong("id") == id) {
            addResources(srs, languageHolder, validator);
            if (!srs.next()) {
                break;
            }
        }
        return validator;
    }

    /**
     * Створити сутність {@link Acsk} з набору даних
     * @param srs набір даних
     * @param languageHolder утримувач мов
     * @return сутність {@link Acsk} з набору даних
     */
    public static Acsk mapAcsk(SqlRowSet srs, LanguageHolder languageHolder) {
        Long id = srs.getLong("id");
        String cmpAddress = srs.getString("cmp_address");
        Integer cmpPort = srs.getInt("cmp_port");
        String nameCode = srs.getString("name_const");

        Acsk acsk = new Acsk();
        acsk.setId(id);
        acsk.setCmpAddress(cmpAddress);
        acsk.setCmpPort(cmpPort);
        acsk.setNameCode(nameCode);
        while(srs.getLong("id") == id) {
            addResources(srs, languageHolder, acsk);
            if (!srs.next()) {
                break;
            }
        }
        return acsk;
    }

    /**
     * Додати ресурс для мови
     * @param srs набір даних
     * @param languageHolder утримувач мов
     * @param source джерело
     */
    public static void addResources(SqlRowSet srs, LanguageHolder languageHolder, Object source) {
        if (source instanceof Nameable) {
            Nameable n = (Nameable) source;
            String nameLangCode = srs.getString("name_lang");
            if (nameLangCode != null) {
                String constName = srs.getString("name_value");
                CommonLocalized loc = LocaleUtil.getLocalized(nameLangCode, constName, languageHolder);
                n.addNameResource(loc);
            }
        }
        if (source instanceof Descable) {
            Descable d = (Descable) source;
            String nameLangCode = srs.getString("descr_lang");
            if (nameLangCode != null) {
                String constName = srs.getString("descr_value");
                CommonLocalized loc = LocaleUtil.getLocalized(nameLangCode, constName, languageHolder);
                d.addDescResource(loc);
            }
        }
    }
}
