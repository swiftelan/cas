package org.apereo.cas.support.saml.web.idp.profile.builders.enc;

import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.EncodingUtils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * This is {@link SamlAttributeEncoder}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
public class SamlAttributeEncoder implements ProtocolAttributeEncoder {

    @Override
    public Map<String, Object> encodeAttributes(final Map<String, Object> attributes, final RegisteredService service) {
        val finalAttributes = Maps.<String, Object>newHashMapWithExpectedSize(attributes.size());

        attributes.forEach((k, v) -> {
            val attributeName = EncodingUtils.hexDecode(k);
            if (StringUtils.isNotBlank(attributeName)) {
                LOGGER.debug("Decoded SAML attribute [{}] to [{}] with value(s) [{}]", k, attributeName, v);
                finalAttributes.put(attributeName, v);
            } else {
                LOGGER.debug("Unable to decode SAML attribute [{}]; accepting it verbatim", k);
                finalAttributes.put(k, v);
            }
        });
        return finalAttributes;
    }
}

