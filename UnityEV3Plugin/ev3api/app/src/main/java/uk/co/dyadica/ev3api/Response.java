package uk.co.dyadica.ev3api;

import uk.co.dyadica.ev3api.EV3Types.*;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public class Response
{
    ReplyType replyType = ReplyType.DirectReplyError;
    int sequence;
    ManualResetEvent event;
    byte[] data;
    SystemOpcode systemCommand = SystemOpcode.BeginDownload;
    SystemReplyStatus systemReplyStatus = SystemReplyStatus.UnknownError;

    public Response(int sequence) {
        this.sequence = sequence;
        event = new ManualResetEvent(false);
    }

    /**
     * Return data of the response
     * @return
     */
    public byte[] getData(){
        return data;
    }
}
