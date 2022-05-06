package ${package.Parent};

import ${package.Entity}.${entity};
import ${package.Entity}.qo.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${entity}Struct {

${entity}Struct INSTANCE = Mappers.getMapper(${entity}Struct.class);

${entity} to(${entity}SaveQO obj);

${entity} to(${entity}UpdateQO obj);
}

