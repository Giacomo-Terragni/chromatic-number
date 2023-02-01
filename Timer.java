public class Timer{

	//variables to store the start and end time of the program
	private long start;
    private long end;

    //method to start the stopwatch
    public void ExecutionTimer() {
        reset();
        start = System.currentTimeMillis();
    }
    //method to stop the stopwatch
    public void end() {
        end = System.currentTimeMillis();
    }
    //method to output the result of the stopwatch
    public double duration(){
        return (end-start);
    }
    //method to reset the two variables 
    public void reset() {
        start = 0;  
        end   = 0;
    }
}