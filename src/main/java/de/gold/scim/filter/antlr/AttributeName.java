package de.gold.scim.filter.antlr;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * author Pascal Knueppel <br>
 * created at: 16.10.2019 - 12:24 <br>
 * <br>
 * represents the name of an attribute and will also hold its meta data
 */
@Getter
@EqualsAndHashCode
public class AttributeName
{

  /**
   * the resource uri to which this attribute belongs e.g.
   * {@link de.gold.scim.constants.SchemaUris#ENTERPRISE_USER_URI}
   */
  private String resourceUri;

  /**
   * the short name of the attribute e.g. 'userName' or 'name.givenName'
   */
  private String shortName;

  /**
   * the fully qualified name of this attribute {@link #resourceUri} + {@link #shortName}
   */
  private String fullName;

  public AttributeName(ScimFilterParser.AttributePathContext attributePathContext)
  {
    this.shortName = attributePathContext.attribute.getText()
                     + StringUtils.stripToEmpty(attributePathContext.subattribute == null ? null
                       : "." + attributePathContext.subattribute.getText());
    this.resourceUri = resolveResourceUri(attributePathContext).orElse(null);
    this.fullName = (resourceUri == null ? "" : StringUtils.stripToEmpty(resourceUri) + ":") + shortName;
  }

  /**
   * tries to resolve the resourceUri value
   *
   * @param attributePathContext the antlr attribute path context
   * @return the resourceUri
   */
  private Optional<String> resolveResourceUri(ScimFilterParser.AttributePathContext attributePathContext)
  {
    if (attributePathContext.resourceUri == null)
    {
      return Optional.empty();
    }
    String resourceUri = StringUtils.stripToEmpty(attributePathContext.resourceUri.getText());
    return Optional.of(StringUtils.stripToNull(resourceUri.replaceFirst(":$", "")));
  }

  @Override
  public String toString()
  {
    return fullName;
  }
}
