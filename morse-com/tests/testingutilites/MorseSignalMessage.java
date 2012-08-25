package testingutilites;

import static org.easymock.EasyMock.reportMatcher;

import org.easymock.IArgumentMatcher;

import robsoninc.morse.utilities.MorseStringConverter;

import android.os.Message;


public class MorseSignalMessage implements IArgumentMatcher
{

    private int morseSignal;

    public MorseSignalMessage(int morseSignal)
    {
        super();
        this.morseSignal = morseSignal;
    }

    @Override
    public boolean matches(Object actual)
    {
        if (!(actual instanceof Message))
        {
            return false;
        }
        else
        {
            Message actualMsg = (Message) actual;
            return actualMsg.arg1 == this.morseSignal;
        }
    }

    @Override
    public void appendTo(StringBuffer buffer)
    {
        switch (this.morseSignal)
            {
                case MorseStringConverter.DIT:
                    buffer.append("Message with Morse DIT Signal");
                    break;
                case MorseStringConverter.DAH:
                    buffer.append("Message with Morse DAH Signal");
                    break;
                case MorseStringConverter.SPACE:
                    buffer.append("Message with Morse SPACE Signal");
                    break;
                default:
                	buffer.append("Message with signal = " + morseSignal);
                	break;
            }
    }
    
    public static <T extends Message> T morseMessageSignal(int in) {
        reportMatcher(new MorseSignalMessage(in));
        return null;
    }
}