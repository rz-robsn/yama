package robsoninc.morse.utilities;

public interface MorseSignal
{
    public final int DIT = 0;
    public final int DAH = 1;
    public final int SPACE = 2;
    
    public void onDit();

    public void onDah();

    public void onSpace();
}
