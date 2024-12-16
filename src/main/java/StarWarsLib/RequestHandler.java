package StarWarsLib;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RequestHandler {
    private final SWAPIService swapiService;
    public final Dictionary dictionary;
    private ObjectMapper objectMapper;
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);
    public boolean isRussianDictionary;

    public RequestHandler() {
        this.swapiService = new SWAPIService();
        this.dictionary = new Dictionary();
        this.objectMapper = new ObjectMapper();
        this.isRussianDictionary = false;
    }

    public String handleRequest(String query) {
        logger.info("Обрабатываем запрос: {}", query);

        String translatedQuery = isRussianDictionary ? dictionary.translate(query) : query;
        if (translatedQuery == null) {
            return "Перевод термина не найден. Пожалуйста, введите корректный запрос.";
        }

        String resourceType = getResourceType(translatedQuery);
        if (resourceType == null) {
            return "Запрос не распознан. Пожалуйста, проверьте ввод или смените язык.";
        }

        if (!checkApiAvailability()) {
            logger.error("Ошибка подключения к SWAPI.");
            return "Ошибка подключения к SWAPI. Пожалуйста, проверьте соединение.";
        }

        Response response = swapiService.searchResource(resourceType, translatedQuery);

        if (response != null && response.statusCode() == 200) {
            switch (resourceType) {
                case "people":
                    List<CharacterDTO> characters = response.jsonPath().getList("results", CharacterDTO.class);
                    return characters.isEmpty() ? "Персонаж не найден." : displayCharacterInfo(characters.get(0));
                case "planets":
                    List<PlanetDTO> planets = response.jsonPath().getList("results", PlanetDTO.class);
                    return planets.isEmpty() ? "Планета не найдена." : displayPlanetInfo(planets.get(0));
                case "films":
                    List<FilmDTO> films = response.jsonPath().getList("results", FilmDTO.class);
                    return films.isEmpty() ? "Фильм не найден." : displayFilmInfo(films.get(0));
                case "species":
                    List<SpeciesDTO> species = response.jsonPath().getList("results", SpeciesDTO.class);
                    return species.isEmpty() ? "Вид не найден." : displaySpeciesInfo(species.get(0));
                case "vehicles":
                    List<VehicleDTO> vehicles = response.jsonPath().getList("results", VehicleDTO.class);
                    return vehicles.isEmpty() ? "Транспортное средство не найдено." : displayVehicleInfo(vehicles.get(0));
                case "starships":
                    List<StarshipDTO> starships = response.jsonPath().getList("results", StarshipDTO.class);
                    return starships.isEmpty() ? "Звездный корабль не найден." : displayStarshipInfo(starships.get(0));
                default:
                    return "Неизвестный тип ресурса.";
            }
        } else {
            logger.error("Ошибка при получении данных для запроса '{}': ресурс не найден.", translatedQuery);
            return "Ошибка при получении данных или ресурс не найден.";
        }
    }

    private String getResourceType(String query) {
        String resourceType = dictionary.getTerms(isRussianDictionary).get(query);
        if (resourceType != null) {
            return resourceType;
        }

        resourceType = dictionary.getTerms(!isRussianDictionary).get(query);
        return resourceType;
    }

    private String displayPlanetInfo(PlanetDTO planet) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(planet.getName()).append("\n")
                .append("Rotation Period: ").append(planet.getRotationPeriod()).append("\n")
                .append("Orbital Period: ").append(planet.getOrbitalPeriod()).append("\n")
                .append("Diameter: ").append(planet.getDiameter()).append("\n")
                .append("Climate: ").append(planet.getClimate()).append("\n")
                .append("Gravity: ").append(planet.getGravity()).append("\n")
                .append("Terrain: ").append(planet.getTerrain()).append("\n")
                .append("Surface Water: ").append(planet.getSurfaceWater()).append("\n")
                .append("Population: ").append(planet.getPopulation()).append("\n");
        logger.info("Выведена информация о планете: {}", planet.getName());
        return info.toString();
    }

    private boolean checkApiAvailability() {
        try {
            Response response = swapiService.getResource("people", "1");
            return response.statusCode() == 200;
        } catch (Exception e) {
            logger.error("Ошибка при проверке доступности SWAPI: {}", e.getMessage());
            return false;
        }
    }

    private String displayCharacterInfo(CharacterDTO character) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(character.getName()).append("\n")
                .append("Height: ").append(character.getHeight()).append("\n")
                .append("Mass: ").append(character.getMass()).append("\n")
                .append("Films: \n");
        for (String film : character.getFilms()) {
            info.append(" - ").append(film).append("\n");
        }
        logger.info("Выведена информация о персонаже: {}", character.getName());
        return info.toString();
    }

    private String displayFilmInfo(FilmDTO film) {
        StringBuilder info = new StringBuilder();
        info.append("Title: ").append(film.getTitle()).append("\n")
                .append("Episode ID: ").append(film.getEpisodeId()).append("\n")
                .append("Director: ").append(film.getDirector()).append("\n")
                .append("Producer: ").append(film.getProducer()).append("\n")
                .append("Release Date: ").append(film.getReleaseDate()).append("\n");
        logger.info("Выведена информация о фильме: {}", film.getTitle());
        return info.toString();
    }

    private String displaySpeciesInfo(SpeciesDTO species) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(species.getName()).append("\n")
                .append("Classification: ").append(species.getClassification()).append("\n")
                .append("Average Lifespan: ").append(species.getAverageLifespan()).append("\n");
        logger.info("Выведена информация о виде: {}", species.getName());
        return info.toString();
    }

    private String displayVehicleInfo(VehicleDTO vehicle) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(vehicle.getName()).append("\n")
                .append("Model: ").append(vehicle.getModel()).append("\n")
                .append("Manufacturer: ").append(vehicle.getManufacturer()).append("\n");
        logger.info("Выведена информация о транспортном средстве: {}", vehicle.getName());
        return info.toString();
    }

    private String displayStarshipInfo(StarshipDTO starship) {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(starship.getName()).append("\n")
                .append("Model: ").append(starship.getModel()).append("\n")
                .append("Manufacturer: ").append(starship.getManufacturer()).append("\n");
        logger.info("Выведена информация о звездном корабле: {}", starship.getName());
        return info.toString();
    }

    public void setDictionary(boolean isRussian) {
        this.isRussianDictionary = isRussian;
    }
}
