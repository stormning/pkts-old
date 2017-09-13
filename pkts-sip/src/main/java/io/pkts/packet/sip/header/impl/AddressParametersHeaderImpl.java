/**
 * 
 */
package io.pkts.packet.sip.header.impl;

import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.address.Address;
import io.pkts.packet.sip.header.AddressParametersHeader;
import io.pkts.packet.sip.header.FromHeader;
import io.pkts.packet.sip.header.Parameters;
import io.pkts.packet.sip.header.ToHeader;


/**
 * A base class for all headers that implmenets both the {@link Address} and {@link Parameters}
 * interfaces, such as the {@link ToHeader} and {@link FromHeader}. However, users must be able to
 * create to create other {@link AddressParametersHeader}s that are unknown to this implementation
 * so they can either extend this base class or simply just create a new
 * {@link AddressParametersHeader} by using the {@link Builder}.
 * 
 * @author jonas@jonasborjesson.com
 */
public class AddressParametersHeaderImpl extends ParametersImpl implements AddressParametersHeader {

    public static final Buffer TAG = Buffers.wrap("tag");

    private final Address address;

    /**
     * @param name
     * @param params
     */
    public AddressParametersHeaderImpl(final Buffer name, final Address address, final Buffer params) {
        super(name, params);
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Buffer getValue() {
        // TODO: create a composite buffer instead of this crap
        final StringBuilder sb = new StringBuilder();
        sb.append(this.address.toString());
        final Buffer superValue = super.getValue();
        if (superValue != null) {
            sb.append(superValue.toString());
        }
        return Buffers.wrap(sb.toString());
    }


    @Override
    protected void transferValue(final Buffer dst) {
        this.address.getBytes(dst);
        super.transferValue(dst);
    }

    @Override
    public AddressParametersHeader ensure() {
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AddressParametersHeaderImpl other = (AddressParametersHeaderImpl) obj;
        if (this.address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!this.address.equals(other.address)) {
            return false;
        }
        return true;
    }

}
