/**
 * 
 */
package io.pkts.packet.sip.header.impl;

import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.SipParseException;
import io.pkts.packet.sip.address.Address;
import io.pkts.packet.sip.header.ContactHeader;


/**
 * @author jonas@jonasborjesson.com
 */
public class ContactHeaderImpl extends AddressParametersHeaderImpl implements ContactHeader {

    /**
     * @param name
     * @param address
     * @param params
     */
    public ContactHeaderImpl(final Address address, final Buffer params) {
        super(ContactHeader.NAME, address, params);
    }

    @Override
    public ContactHeader clone() {
        final Buffer buffer = Buffers.createBuffer(1024);
        transferValue(buffer);
        try {
            return ContactHeader.frame(buffer);
        } catch (final SipParseException e) {
            throw new RuntimeException("Unable to clone the Contact-header", e);
        }
    }

    @Override
    public ContactHeader ensure() {
        return this;
    }

}
