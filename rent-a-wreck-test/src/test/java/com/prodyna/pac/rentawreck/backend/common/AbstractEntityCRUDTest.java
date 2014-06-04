/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Assert;
import org.junit.Test;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;

/**
 * AbstractEntityCRUDTest
 *
 * @author Oliver Teichmann
 *
 */
public abstract class AbstractEntityCRUDTest<T extends AbstractEntity> {
	
	protected abstract AbstractEntityPersistenceService<T> getService();
	
	protected abstract T getCRUDEntity();

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testCRUDOperations(){
		
		T entity = getCRUDEntity();
		
		Assert.assertNull(getService().findById(entity.getUuid()));
		
		T persitedEntity = getService().create(entity);
		
		T updatedEntity = getService().update(persitedEntity);
		
		getService().delete(updatedEntity.getUuid());
		
		Assert.assertNull(getService().findById(entity.getUuid()));
		
	}
}
