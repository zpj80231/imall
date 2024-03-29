package com.imall.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * @author zhangpengjun
 * @date 2023/7/13
 */
public class ElParser {

    public static String parse(String elString, Map<String, Object> map) {
        elString = String.format("#{%s}", elString);
        // 创建表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        map.forEach(context::setVariable);

        // 解析表达式
        Expression expression = parser.parseExpression(elString, new TemplateParserContext());
        // 使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        return expression.getValue(context, String.class);
    }

}
