package io.pkts.sdp;

import gov.nist.core.Debug;
import gov.nist.core.Token;
import gov.nist.javax.sdp.fields.MediaField;
import gov.nist.javax.sdp.fields.SDPField;
import gov.nist.javax.sdp.parser.Lexer;
import gov.nist.javax.sdp.parser.SDPParser;

import java.text.ParseException;
import java.util.Vector;

/**
 * .
 *
 * @author stormning 2017/11/30
 * @since 1.3.0
 */
public class MediaFieldParserEx extends SDPParser {

    /**
     * Creates new MediaFieldParser
     */
    public MediaFieldParserEx(String mediaField) {
        lexer = new Lexer("charLexer", mediaField);
    }

    public MediaField mediaField() throws ParseException {
        if (Debug.parserDebug)
            dbg_enter("mediaField");
        try {
            MediaField mediaField = new MediaField();

            lexer.match('m');
            lexer.SPorHT();
            lexer.match('=');
            lexer.SPorHT();

            lexer.match(Lexer.ID);
            Token media = lexer.getNextToken();
            mediaField.setMedia(media.getTokenValue());
            this.lexer.SPorHT();

            lexer.match(Lexer.ID);
            Token port = lexer.getNextToken();
            mediaField.setPort(Integer.parseInt(port.getTokenValue()));

            this.lexer.SPorHT();

            // Some strange media formatting from Sun Ray systems with media
            // reported by Emil Ivov and Iain Macdonnell at Sun
            if (lexer.hasMoreChars() && lexer.lookAhead(1) == '\n')
                return mediaField;

            if (lexer.lookAhead(0) == '/') {
                // The number of ports is present:
                lexer.consume(1);
                lexer.match(Lexer.ID);
                Token portsNumber = lexer.getNextToken();
                mediaField.setNports(
                        Integer.parseInt(portsNumber.getTokenValue()));
                this.lexer.SPorHT();
            }

            lexer.match(Lexer.ID);
            Token token = lexer.getNextToken();
            this.lexer.SPorHT();
            String transport = token.getTokenValue();
            while (lexer.lookAhead(0) == '/') {
                lexer.consume(1);
                lexer.match(Lexer.ID);
                Token transportTemp = lexer.getNextToken();
                transport = transport + "/" + transportTemp.getTokenValue();
                this.lexer.SPorHT();
            }

            mediaField.setProto(transport);

            // The formats list:
            Vector formatList = new Vector();
            while (lexer.hasMoreChars()) {
                char la = lexer.lookAhead(0);
                if (la == '\n' || la == '\r')
                    break;
                this.lexer.SPorHT();
                //while(lexer.lookAhead(0) == ' ') lexer.consume(1);
                lexer.match(Lexer.ID);
                Token tok = lexer.getNextToken();
                this.lexer.SPorHT();
                String format = tok.getTokenValue().trim();
                if (!format.equals(""))
                    formatList.add(format);
            }
            mediaField.setFormats(formatList);

            return mediaField;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(lexer.getPtr());
            //System.out.println("char = [" + lexer.lookAhead(0) +"]");
            throw new ParseException(lexer.getBuffer(), lexer.getPtr());
        } finally {
            dbg_leave("mediaField");
        }
    }

    public SDPField parse() throws ParseException {
        return this.mediaField();
    }
}

/**
 * public static void main(String[] args) throws ParseException {
 * String media[] = {
 * "m=video 0\r\n",
 * "m=audio 50006 RTP/AVP 0 8 4 18\r\n",
 * "m=video 49170/2 RTP/AVP 31\n",
 * "m=video 49232 RTP/AVP 0\n",
 * "m=audio 49230 RTP/AVP 96 97 98\n",
 * "m=application 32416 udp wb\n",
 * "m=control 49234 H323 mc\n",
 * "m=audio 50012 RTP/AVP 0 8 4 18\n",
 * "m=image 49172 udptl t38\n"
 * };
 * <p>
 * for (int i = 0; i < media.length; i++) {
 * MediaFieldParser mediaFieldParser=new MediaFieldParser(
 * media[i] );
 * System.out.println("toParse: " + media[i]);
 * MediaField mediaField=mediaFieldParser.mediaField();
 * System.out.println("encoded: " +mediaField.encode());
 * }
 * <p>
 * }
 **/