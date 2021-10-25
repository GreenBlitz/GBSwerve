package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
import org.greenblitz.debug.RemoteCSVTarget;

import java.security.KeyPair;
import java.util.*;

public class GraphEncoderVoltage extends GBCommand {

    private static final long TIMEOUT = 200;
    private RemoteCSVTarget logger;
    private LinkedList<Pair<Long, Double>> values;
    private long t0;

    public GraphEncoderVoltage() {
        this.logger = RemoteCSVTarget.initTarget("SwerveEncoderVoltage", "time", "volt");
        this.values = new LinkedList<>();
    }

    @Override
    public void initialize() {
        super.initialize();
        this.t0 = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("running");
        SingleModule instance = SingleModule.getInstance();
//        logger.report((System.currentTimeMillis() - this.t0) / 1000.0, instance.getModule().getTicks());
        values.add(new Pair<>(System.currentTimeMillis() - this.t0, instance.getModule().getTicks()));
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        int size = values.size();
        while (!values.isEmpty()) {
            System.out.println("Sent");
            logger.report(values.peek().first / 1000.0, values.remove().second);
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(size);
    }

    private class Pair<F, E> {
        private F first;
        private E second;

        public Pair(F first, E second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return this.first;
        }

        public void setFirst(F value) {
            this.first = value;
        }

        public E getSecond() {
            return this.second;
        }

        public void setSecond(E value) {
            this.second = value;
        }
    }

}
