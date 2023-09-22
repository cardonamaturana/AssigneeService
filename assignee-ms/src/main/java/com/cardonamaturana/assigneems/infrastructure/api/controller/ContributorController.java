package com.cardonamaturana.assigneems.infrastructure.api.controller;

import com.cardonamaturana.assigneems.application.assignee.AssigneeSaveApplication;
import com.cardonamaturana.assigneems.domain.entity.Contributor;
import com.cardonamaturana.assigneems.infrastructure.api.dto.request.contributor.ContributorRequest;
import com.cardonamaturana.assigneems.infrastructure.api.dto.response.contributor.ContributorResponse;
import com.cardonamaturana.assigneems.infrastructure.api.mapper.contributor.ContributorRequestMapper;
import com.cardonamaturana.assigneems.infrastructure.api.mapper.contributor.ContributorResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contributor")
@RequiredArgsConstructor
public class ContributorController {

  private final AssigneeSaveApplication assigneeSaveApplication;
  private final ContributorRequestMapper contributorRequestMapper;
  private final ContributorResponseMapper contributorResponseMapper;

  @PostMapping()
  @Operation(summary = "Create Contributor Responsible", description = "Create a new Contributor into database", responses = {
      @ApiResponse(responseCode = "200", description = "create successful"),
      @ApiResponse(responseCode = "400", description = "error in any field of JSON request")})
  public Mono<ResponseEntity<ContributorResponse>> saveContributor(
      @RequestBody ContributorRequest contributorRequest) {
    return assigneeSaveApplication.save(contributorRequestMapper.toEntity(contributorRequest))
        .map(saved -> ResponseEntity.status(
            HttpStatus.CREATED).body(contributorResponseMapper.toDto((Contributor) saved)));
  }

}
