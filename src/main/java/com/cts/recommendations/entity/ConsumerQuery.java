package com.cts.recommendations.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@Data
public class ConsumerQuery {
@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
Integer pk;
String consumerId;
String brands;
String colors;
Integer maxBudget;
String  segments;

}
