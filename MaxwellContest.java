
public class MaxwellContest{
    public float solve (int height, int width, int d, int b, int r, int[][] particles){
        MaxwellContainer container = new MaxwellContainer(height, width, d, b, r, particles,true);
        float solutionTime = container.solve();
        if (solutionTime < 0){
            JOptionPane.showMessageDialog(null, "Impossible");
            return solutionTime;
        }
        else{
           JOptionPane.showMessageDialog(null, "tiempo de solucion minimo: "+ solutionTime); 
           return solutionTime;
        }
    }

        public void simulate (int height, int width, int d, int b, int r, int[][] particles){
        int solutionTime = (int)solve(height, width, d, b, r, particles);
        if (solutionTime > 0){
            MaxwellContainer containerSim = new MaxwellContainer(height, width, d, b, r, particles);
            containerSim.start(solutionTime);
        }
    }
}