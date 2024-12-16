package StarWarsLib;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private final Map<String, String> russianTerms; // Соответствия русских терминов и типов ресурсов
    private final Map<String, String> englishTerms; // Соответствия английских терминов и типов ресурсов
    private final Map<String, String> translations; // Соответствия между русскими и английскими терминами

    public Dictionary() {
        russianTerms = new HashMap<>();
        russianTerms.put("Люк Скайуокер", "people");
        russianTerms.put("Йода", "people");
        russianTerms.put("Татуин", "planets");
        russianTerms.put("Дагоба", "planets");
        russianTerms.put("Дарт Вейдер", "people");
        russianTerms.put("Рей", "people");
        russianTerms.put("Алдераан", "planets");
        russianTerms.put("Звезда смерти", "starships");
        russianTerms.put("Новая надежда", "films");
        russianTerms.put("Р5-Д4", "people");
        russianTerms.put("Ши Скайуокер", "people");
        russianTerms.put("Набу", "planets");
        russianTerms.put("Мон Кала", "planets");
        russianTerms.put("Атака клонов","films");
        russianTerms.put("Оуэн Ларс", "people");
        russianTerms.put("Гридо", "people");

        englishTerms = new HashMap<>();
        englishTerms.put("Luke Skywalker", "people");
        englishTerms.put("Yoda", "people");
        englishTerms.put("Tatooine", "planets");
        englishTerms.put("Dagobah", "planets");
        englishTerms.put("Darth Vader", "people");
        englishTerms.put("Rey", "people");
        englishTerms.put("Alderaan", "planets");
        englishTerms.put("Death Star", "starships");
        englishTerms.put("A New Hope", "films");
        englishTerms.put("R5-D4", "people");
        englishTerms.put("Shmi Skywalker", "people");
        englishTerms.put("Naboo", "planets");
        englishTerms.put("Mon Cala", "planets");
        englishTerms.put("A New Hope", "films");
        englishTerms.put("Owen Lars", "people");
        englishTerms.put("Greedo", "people");

        translations = new HashMap<>();
        translations.put("Люк Скайуокер", "Luke Skywalker");
        translations.put("Йода", "Yoda");
        translations.put("Татуин", "Tatooine");
        translations.put("Дагоба", "Dagobah");
        translations.put("Дарт Вейдер", "Darth Vader");
        translations.put("Рей", "Rey");
        translations.put("Алдераан", "Alderaan");
        translations.put("Звезда смерти", "Death Star");
        translations.put("Новая надежда", "A New Hope");
        translations.put("Р5-Д4", "RP-D4");
        translations.put("Ши Скайуокер", "Shmi Skywalker");
        translations.put("Набу", "Naboo");
        translations.put("Attack of the Clones", "Атака клонов");
        translations.put("Revenge of the Sith", "Месть ситхов");
        translations.put("Owen Lars", "Оуэн Ларс");
        translations.put("Greedo", "Гридо");


    }

    public Map<String, String> getTerms(boolean isRussian) {
        return isRussian ? russianTerms : englishTerms; // Возвращаем термины в зависимости от языка
    }

    public String translate(String russianTerm) {
        return translations.get(russianTerm); // Возврат английского термина
    }
}