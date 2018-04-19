package io.pkts.packet.sip;

import gov.nist.javax.sdp.SessionDescriptionImpl;
import gov.nist.javax.sdp.fields.MediaField;
import io.pkts.sdp.MediaFieldParserEx;
import io.pkts.sdp.SDPAnnounceParserEx;

import java.text.ParseException;

/**
 * .
 *
 * @author stormning 2017/11/30
 * @since 1.3.0
 */
public class Test {
    public static void main(String[] args) throws ParseException {
        SDPAnnounceParserEx ex = new SDPAnnounceParserEx(
                "v=0\n" +
                "o=aaaaaaaaaaaaaaaaaaaa 0 0 IN IP4 192.168.30.254\n" +
                "s=Play\n" +
                "c=IN IP4 192.168.30.254\n" +
                "t=0 0\n" +
                "m=video 20002 TCP/RTP/AVP 96 98 97\n" +
                "a=recvonly\n" +
                "a=rtpmap:96 PS/90000\n" +
                "a=rtpmap:98 H264/90000\n" +
                "a=rtpmap:97 MPEG4/90000\n" +
                "a=setup:active\n" +
                "a=connection:new\n" +
                "y=0000001024\n" +
                "f=");

        SessionDescriptionImpl parse = ex.parse();

        System.out.println(parse);
//
//        String media = "m=video 49170/2 TCP/RTP/AVP 31";
//        MediaFieldParserEx mediaFieldParser = new MediaFieldParserEx(media);
//        MediaField mediaField = mediaFieldParser.mediaField();
//        System.out.println("encoded: " + mediaField.encode());
    }
}