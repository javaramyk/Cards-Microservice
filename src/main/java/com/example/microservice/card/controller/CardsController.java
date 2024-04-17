package com.example.microservice.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.card.constants.CardsConstants;
import com.example.microservice.card.dto.CardsDto;
import com.example.microservice.card.dto.ErrorResponseDto;
import com.example.microservice.card.dto.ResponseDto;
import com.example.microservice.card.service.ICardsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(
	name="CRUD Rest APIs for Cards in Spring Boot",
	description="CRUD REST APIS in Springboot to create,uodate,fetch and delete card"
	)
@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

	@Autowired
	private ICardsService iCardsService;
	
	@Operation(summary="Create Card REST API",description="Rest API to create new Card inside SPringboot")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description="HTTP Status CREATED"),
		@ApiResponse(responseCode="500",description="HTTp Status Internal Server Error", content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message="mobile number must be 10 digits") String mobileNumber){
		iCardsService.createCard(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
	}
	
	@Operation(summary="Fetch Card Details REst API",description="Rest API to fetch card details based on mobile number")
	@ApiResponses({
		@ApiResponse(responseCode="200",description="HTTP Status OK"),
		@ApiResponse(responseCode="500",description="HTTP status Internal Server Error",content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
	})
	@GetMapping("/fetch")
	public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10}))",message="Mobile number must be 10 digits") String mobileNumber){
		CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
	}
	
	@Operation(summary="Update Card Details REST API",description="REST API to update card details based on a card Number")
	@ApiResponses({
		@ApiResponse(responseCode="200",description="HTTP Status OK"),
		@ApiResponse(responseCode="417",description="Exceptation Failed"),
		@ApiResponse(responseCode="500",description="HTTP Status Internal Sever Error",content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
	})
	@PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }
	
	@Operation(summary="Delete card Details REST API",description="REST API to delete Card details based on a mobile number")
	@ApiResponses({
		@ApiResponse(responseCode="200",description="HTTP Status OK"),
		@ApiResponse(responseCode="417",description="Exceptation Failed"),
		@ApiResponse(responseCode="500",description="HTTP Status Interval Server Error",content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
		
	})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number must be 10 digits number") String mobileNumber){
		boolean isDeleted = iCardsService.deleteCard(mobileNumber);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
		}
		else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
		}		
	}
}
