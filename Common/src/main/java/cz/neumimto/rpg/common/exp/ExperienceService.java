package cz.neumimto.rpg.common.exp;

public interface ExperienceService {

    void load();

    void reload();

    Double getMiningExperiences(String type);

    Double getLoggingExperiences(String type);

    Double getFishingExperience(String type);

    Double getFarmingExperiences(String type);
}
