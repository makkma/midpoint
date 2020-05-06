/*
 * Copyright (c) 2020 Evolveum and contributors
 *
 * This work is dual-licensed under the Apache License 2.0
 * and European Union Public License. See LICENSE file for details.
 */
package com.evolveum.midpoint.gui.impl.factory.panel;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.evolveum.midpoint.gui.api.factory.GuiComponentFactory;
import com.evolveum.midpoint.gui.api.registry.GuiComponentRegistry;
import com.evolveum.midpoint.gui.api.prism.wrapper.PrismPropertyWrapper;
import com.evolveum.midpoint.web.component.prism.InputPanel;

/**
 * abstract factory for all InputPanel panels
 * @param <T>
 */
public abstract class AbstractInputGuiComponentFactory<T> implements GuiComponentFactory<PrismPropertyPanelContext<T>> {

    @Autowired private GuiComponentRegistry componentRegistry;

    public GuiComponentRegistry getRegistry() {
        return componentRegistry;
    }

    @Override
    public Panel createPanel(PrismPropertyPanelContext<T> panelCtx) {
        InputPanel panel = getPanel(panelCtx);
//        panel.append(panelCtx.getAjaxEventBehavior());
//        panel.append(panelCtx.getVisibleEnableBehavior());
//        PrismPropertyWrapper<T> propertyWrapper = panelCtx.unwrapWrapperModel();
//        IModel<String> label = LambdaModel.of(propertyWrapper::getDisplayName);
//        panel.setComponentLabel(label);
//        panel.required(panelCtx.isMandatory());
//

//        final List<FormComponent> formComponents = panel.getFormComponents();
//        for (FormComponent<T> formComponent : formComponents) {
//            PrismPropertyWrapper<T> propertyWrapper = panelCtx.unwrapWrapperModel();
//            IModel<String> label = LambdaModel.of(propertyWrapper::getDisplayName);
//            formComponent.setLabel(label);
//            formComponent.setRequired(panelCtx.isMandatory());
//
//            if (formComponent instanceof TextField) {
//                formComponent.add(new AttributeModifier("size", "42"));
//            }
//            formComponent.add(panelCtx.getAjaxEventBehavior());
//            formComponent.add(panelCtx.getVisibleEnableBehavior());
////            formComponent.add(new EnableBehaviour(() -> getEditabilityHandler() == null ||
////                    getEditabilityHandler().isEditable(getModelObject())));
//        }

//        panel.getValidatableComponent().add(panelCtx.getExpressionValidator());

//        panelCtx.getFeedback().setFilter(new ComponentFeedbackMessageFilter(panel.getValidatableComponent()));


        return panel;
    }

    @Override
    public void configure(PrismPropertyPanelContext<T> panelCtx, Component component) {
        if (!(component instanceof InputPanel)) {
            return;
        }
        InputPanel panel = (InputPanel) component;
        final List<FormComponent> formComponents = panel.getFormComponents();
        for (FormComponent<T> formComponent : formComponents) {
            PrismPropertyWrapper<T> propertyWrapper = panelCtx.unwrapWrapperModel();
            IModel<String> label = LambdaModel.of(propertyWrapper::getDisplayName);
            formComponent.setLabel(label);
            formComponent.setRequired(panelCtx.isMandatory());

            if (formComponent instanceof TextField) {
                formComponent.add(new AttributeModifier("size", "42"));
            }
            formComponent.add(panelCtx.getAjaxEventBehavior());
            formComponent.add(panelCtx.getVisibleEnableBehavior());

//            formComponent.add(new EnableBehaviour(() -> getEditabilityHandler() == null ||
//                    getEditabilityHandler().isEditable(getModelObject())));
        }

        panel.getValidatableComponent().add(panelCtx.getExpressionValidator());
        panelCtx.getFeedback().setFilter(new ComponentFeedbackMessageFilter(panel.getValidatableComponent()));

    }

    @Override
    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }

    protected abstract InputPanel getPanel(PrismPropertyPanelContext<T> panelCtx);
}
