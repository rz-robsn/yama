package testingutilites;

import static org.easymock.EasyMock.reportMatcher;

import org.easymock.IArgumentMatcher;

import android.os.Message;
import android.view.MotionEvent;

public class MotionEventMessage implements IArgumentMatcher
{
	private int motionEvent;
	
	public MotionEventMessage(int motionEvent)
    {
	    super();
	    this.motionEvent = motionEvent;
    }

	@Override
	public boolean matches(Object argument)
	{
        if (!(argument instanceof Message))
        {
            return false;
        }
        else
        {
            Message actualMsg = (Message) argument;
            return actualMsg.arg2 == this.motionEvent;
        }
	}

	@Override
	public void appendTo(StringBuffer buffer)
	{
		switch(this.motionEvent)
		{
			case MotionEvent.ACTION_DOWN:
				buffer.append("ACTION_DOWN MotionEvent.");
				break;
				
			case MotionEvent.ACTION_UP:
				buffer.append("ACTION_UP MotionEvent.");
				break;
		}
	}

    public static <T extends Message> T aMotionEventMessage(int MotionEventCode) {
        reportMatcher(new MotionEventMessage(MotionEventCode));
        return null;
    }	
}
