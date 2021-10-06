package edu.greenblitz.bigRodika.subsystems;

public class SingleModule extends GBSubsystem {
    private SwerveModule module;
    private static SingleModule instance;

    private SingleModule() {
        module = new SwerveModule(0);
    }

    public static SingleModule getInstance() {
        if (instance == null) {
            instance = new SingleModule();
        }
        return instance;
    }

    public SwerveModule getModule() {
        return module;
    }

    public void setModule(SwerveModule module) {
        this.module = module;
    }
}
