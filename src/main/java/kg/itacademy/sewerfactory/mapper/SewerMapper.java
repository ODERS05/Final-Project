package kg.itacademy.sewerfactory.mapper;

import kg.itacademy.sewerfactory.dto.sewer.request.SewerRequest;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Sewer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SewerMapper {
    SewerMapper INSTANCE = Mappers.getMapper(SewerMapper.class);
    Sewer toSewerEntity(SewerRequest request);
    SewerResponse toSewerResponse(Sewer sewer);

    List<SewerResponse> toSewersResponse(List<Sewer> sewer);
}

