package io.pkts.packet.sip;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * .
 *
 * @author stormning 2017/8/31
 * @since 1.3.0
 */
public class XmlContent {

    private byte[] bytes;

    public XmlContent(byte[] bytes) {
        this.bytes = bytes;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(new String(bytes, "gb2312"));
            return (T) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
