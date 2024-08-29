package com.foodtogo.mono.restaurant.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant.core.enums.RestaurantArea;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_restaurants")
public class Restaurant extends LogEntity {

    // 음식점 ID, PK
    @Id
    @UuidGenerator
    private UUID restaurantId;

    //    // 음식점 카테고리 ID, FK
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false )
    private RestaurantCategory category;

    // 음식점 소유자 ID, FK
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false )
    private User user;

    // 가게 지역 (일시적으로 광화문만)
    @Enumerated(EnumType.STRING)
    private RestaurantArea area;

    // 음식점 운영상태
    private Boolean isOpened;

    // 음식점 이름
    private String restaurantName;

    // 음식점 주소
    private String restaurantAddress;

    // 음식점 전화번호
    private String restaurantPhoneNumber;

    // 음식점 소개
    @Column(columnDefinition = "TEXT")
    private String restaurantIntroduce;

    // 이미지 URL
    @Column(columnDefinition = "TEXT")
    private String restaurantImageUrl;

    public Restaurant(
            RestaurantRequestDto requestDto
            , String createdBy
            , User user
            , RestaurantCategory category
    ){
        this.restaurantName = requestDto.getRestaurantName();
        this.restaurantAddress = requestDto.getRestaurantAddress();
        this.restaurantPhoneNumber = requestDto.getRestaurantPhoneNumber();
        this.restaurantIntroduce = requestDto.getRestaurantIntroduce();
        this.restaurantImageUrl = requestDto.getRestaurantImageUrl();
        this.user = user;
        this.category = category;
        this.isOpened = Boolean.TRUE;
        this.area = RestaurantArea.광화문;

        setCreatedBy(createdBy);
    }

    public void updateRestaurants(
            RestaurantRequestDto requestDto
            , String updatedBy
    ){
        this.restaurantName = requestDto.getRestaurantName();
        this.restaurantAddress = requestDto.getRestaurantAddress();
        this.restaurantPhoneNumber = requestDto.getRestaurantPhoneNumber();
        this.restaurantIntroduce = requestDto.getRestaurantIntroduce();
        this.restaurantImageUrl = requestDto.getRestaurantImageUrl();

        setUpdatedBy(updatedBy);
    }
    // 삭제일자 / 삭제자 작성
    public void deleteRestaurants(
            String deletedBy
    ) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }

    public void setIsOpened(Boolean isOpened) {
        this.isOpened = isOpened;
    }
}
