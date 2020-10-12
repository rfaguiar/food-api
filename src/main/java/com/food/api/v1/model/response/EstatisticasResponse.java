package com.food.api.v1.model.response;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estatisticas")
public class EstatisticasResponse extends RepresentationModel<EstatisticasResponse> {
}
