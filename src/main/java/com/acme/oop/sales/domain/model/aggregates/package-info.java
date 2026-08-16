/**
 * Sales Bounded-Context Aggregates and Entities.
 * <p>
 * This package encapsulates sales order aggregates and line items, enforcing order totals,
 * line item quantities, and currency consistency invariants.
 * </p>
 *
 * <ul>
 *   <li>{@link com.acme.oop.sales.domain.model.aggregates.SalesOrder} - Sales Order Aggregate Root</li>
 *   <li>{@link com.acme.oop.sales.domain.model.aggregates.SalesOrderItem} - Internal line item entity</li>
 * </ul>
 *
 * @author Open Source Application Development Team
 * @since 1.0.0
 */
package com.acme.oop.sales.domain.model.aggregates;
