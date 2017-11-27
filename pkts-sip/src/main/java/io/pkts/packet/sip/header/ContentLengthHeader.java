package io.pkts.packet.sip.header;

import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.SipParseException;
import io.pkts.packet.sip.header.impl.ContentLengthHeaderImpl;
import io.pkts.packet.sip.impl.SipParser;

import static io.pkts.packet.sip.impl.PreConditions.assertArgument;

/**
 * .
 *
 * @author stormning 2017/9/13
 * @since 1.3.0
 */
public interface ContentLengthHeader extends SipHeader {

    Buffer NAME = Buffers.wrap("Content-Length");

    long getLength();

    @Override
    ContentLengthHeader clone();

    /**
     * Parse the value as a length value. This method assumes that you have already parsed out the
     * actual header name "CSeq: "
     *
     * @param value
     * @return
     * @throws SipParseException
     */
    public static ContentLengthHeader frame(final Buffer value) throws SipParseException {
        final Buffer length = SipParser.expectDigit(value);
        final long number = Long.parseLong(length.toString());
        return new ContentLengthHeaderImpl(number);
    }

    static ContentLengthHeaderBuilder with() {
        return new ContentLengthHeaderBuilder();
    }

    static class ContentLengthHeaderBuilder {

        private long length;

        private ContentLengthHeaderBuilder() {
            // left empty intentionally
        }

        /**
         * @param length
         * @return
         * @throws SipParseException in case the specified sequence number is less than zero.
         */
        public ContentLengthHeaderBuilder length(final long length) throws SipParseException {
            assertArgument(length >= 0, "Sequence number must be greater or equal to zer");
            this.length = length;
            return this;
        }

        public ContentLengthHeader build() {
            return new ContentLengthHeaderImpl(length);
        }

    }
}
