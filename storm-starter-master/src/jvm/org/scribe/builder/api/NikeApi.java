package org.scribe.builder.api;

import org.scribe.model.*;

import org.scribe.utils.*;

public class NikeApi extends DefaultApi20
{
  private static final String AUTHORIZE_URL = " ";
  private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + " ";

  @Override
  public String getAccessTokenEndpoint()
  {
    return " ";
  }

  @Override
  public String getAuthorizationUrl(OAuthConfig config)
  {
    Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Nike+ does not support OOB");

    // Append scope if present
    if(config.hasScope())
    {
     return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
    }
    else
    {
      return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
  }
}
