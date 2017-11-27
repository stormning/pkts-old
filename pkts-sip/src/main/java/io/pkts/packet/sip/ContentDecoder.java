package io.pkts.packet.sip;

import io.pkts.buffer.Buffer;
import io.pkts.packet.sip.header.ContentTypeHeader;

/**
 * .
 *
 * @author stormning 2017/8/31
 * @since 1.3.0
 */
public interface ContentDecoder {
    Object decode(ContentTypeHeader contentType, Buffer payload);
}
