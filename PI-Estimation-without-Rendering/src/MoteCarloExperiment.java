import java.awt.*;

public class MoteCarloExperiment {

    private int squareSide;
    private int N;
    private int outputInterval = 100;

    public MoteCarloExperiment(int squareSide, int N){
        if(squareSide <= 0|| N <=0)
            throw new IllegalArgumentException("squareSide and N must larger than 0!");
        this.squareSide = squareSide;
        this.N = N;
    }

    public void setOutputInterval(int interval){
        if(interval <= 0)
            throw new IllegalArgumentException("interval must be larger than zero");
        this.outputInterval = interval;
    }

    public void run(){
        Circle circle = new Circle(squareSide/2, squareSide/2, squareSide/2);
        MonteCarloPiData data = new MonteCarloPiData(circle);

        for(int i =0; i < N; i++){
            int x = (int)(Math.random() * squareSide);
            int y = (int)(Math.random() * squareSide);
            data.addPoint(new Point(x, y));
            if(i % outputInterval == 0)
                System.out.printf("No.%d\tEstimation:\t%s\n", (data.getPointsNumber() + outputInterval - 1), data.estimatePi());
        }
    }

    public static void main(String[] args){
        int squareSide = 800;
        int N = 90000000;

        MoteCarloExperiment exp = new MoteCarloExperiment(squareSide, N);
        // default: 100
        exp.setOutputInterval(10000);
        exp.run();
    }
}
