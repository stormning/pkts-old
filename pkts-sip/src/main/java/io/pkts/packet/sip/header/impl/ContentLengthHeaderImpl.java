package io.pkts.packet.sip.header.impl;

import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.header.ContentLengthHeader;

/**
 * .
 *
 * @author stormning 2017/9/13
 * @since 1.3.0
 */
public class ContentLengthHeaderImpl extends SipHeaderImpl implements ContentLengthHeader {
    private final long length;

    /**
     *
     */
    public ContentLengthHeaderImpl(final long length) {
        super(ContentLengthHeader.NAME, Buffers.wrap(length));
        this.length = length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLength() {
        return this.length;
    }

    @Override
    public Buffer getValue() {
        if (super.getValue() != null) {
            return super.getValue();
        }

        final int size = Buffers.stringSizeOf(this.length);
        final Buffer value = Buffers.createBuffer(size);
        value.writeAsString(this.length);
        return value;
    }

    @Override
    public ContentLengthHeader clone() {
        return new ContentLengthHeaderImpl(this.length);
    }

    @Override
    public ContentLengthHeader ensure() {
        return this;
    }
}
