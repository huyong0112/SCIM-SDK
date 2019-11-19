package de.gold.scim.server.endpoints.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import de.gold.scim.common.constants.enums.SortOrder;
import de.gold.scim.common.exceptions.NotImplementedException;
import de.gold.scim.common.exceptions.ResourceNotFoundException;
import de.gold.scim.common.resources.ResourceNode;
import de.gold.scim.common.schemas.SchemaAttribute;
import de.gold.scim.server.endpoints.ResourceHandler;
import de.gold.scim.server.filter.FilterNode;
import de.gold.scim.server.response.PartialListResponse;
import de.gold.scim.server.schemas.ResourceType;
import de.gold.scim.server.schemas.ResourceTypeFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * author Pascal Knueppel <br>
 * created at: 17.10.2019 - 22:44 <br>
 * <br>
 * the resourceType handler for the resourceType endpoint that will provide the different resources
 */
@Slf4j
@AllArgsConstructor
public class ResourceTypeHandler extends ResourceHandler<ResourceType>
{

  /**
   * creates the error message for the not supported operations
   */
  private static final Function<String, String> ERROR_MESSAGE_SUPPLIER = operation -> {
    return "the '" + operation + "'-operation is not supported for ResourceTypes";
  };

  /**
   * needed for unit tests to prevent application context pollution
   */
  private ResourceTypeFactory resourceTypeFactory;

  /**
   * creating of resource types not supported
   */
  @Override
  public ResourceType createResource(ResourceType resource)
  {
    throw new NotImplementedException(ERROR_MESSAGE_SUPPLIER.apply("create"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResourceType getResource(String id)
  {
    return resourceTypeFactory.getResourceTypeByName(id).orElseThrow(() -> {
      return new ResourceNotFoundException("a ResourceType with the name '" + id + "' does not exist", null, null);
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PartialListResponse listResources(long startIndex,
                                           int count,
                                           FilterNode filter,
                                           SchemaAttribute sortBy,
                                           SortOrder sortOrder,
                                           List<SchemaAttribute> attributes,
                                           List<SchemaAttribute> excludedAttributes)
  {
    List<ResourceNode> resourceTypeList = new ArrayList<>(resourceTypeFactory.getAllResourceTypes());
    return PartialListResponse.builder().resources(resourceTypeList).totalResults(resourceTypeList.size()).build();
  }

  /**
   * updating of resource types not supported
   */
  @Override
  public ResourceType updateResource(ResourceType resourceToUpdate)
  {
    throw new NotImplementedException(ERROR_MESSAGE_SUPPLIER.apply("update"));
  }

  /**
   * deleting of resource types not supported
   */
  @Override
  public void deleteResource(String id)
  {
    throw new NotImplementedException(ERROR_MESSAGE_SUPPLIER.apply("delete"));
  }
}