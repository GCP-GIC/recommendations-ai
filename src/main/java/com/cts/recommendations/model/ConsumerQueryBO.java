package com.cts.recommendations.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class ConsumerQueryBO {
String consumerId;
String brands;
String colors;
Integer maxBudget;
String  segments;
}
