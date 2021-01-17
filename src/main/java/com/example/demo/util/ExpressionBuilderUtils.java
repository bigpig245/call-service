package com.example.demo.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;

import java.time.LocalDate;

public class ExpressionBuilderUtils {
  public static <T> BooleanExpression eqExpression(SimpleExpression<T> expression, T value) {
    return value == null ? null : expression.eq(value);
  }

  public static BooleanExpression betweenExpression(
      DateTimePath dateTimePath, LocalDate fromDate, LocalDate toDate) {
    if (fromDate != null && toDate == null) {
      return dateTimePath.after(DateTimeUtils.getStartOfDateTime(fromDate));
    } else if (fromDate == null && toDate != null) {
      return dateTimePath.before(DateTimeUtils.getEndOfDateTime(toDate));
    } else if (fromDate != null) {
      return dateTimePath.between(
          DateTimeUtils.getStartOfDateTime(fromDate),
          DateTimeUtils.getEndOfDateTime(toDate));
    }

    return null;
  }

}
