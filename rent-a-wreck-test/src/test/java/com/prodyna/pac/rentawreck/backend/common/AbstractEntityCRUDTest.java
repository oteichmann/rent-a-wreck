/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import org.jboss.arquillian.junit.InSequence;
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
	@InSequence(0)
	@Transactional(TransactionMode.ROLLBACK)
	public void testCRUDOperations(){
		
		T entity = getCRUDEntity();
		
		Assert.assertNull(getService().read(entity.getUuid()));
		Assert.assertEquals(0, getService().findAllCount().intValue());
		
		T persitedEntity = getService().create(entity);
		Assert.assertEquals(1, getService().findAllCount().intValue());
		
		T updatedEntity = getService().update(persitedEntity);
		
		getService().delete(updatedEntity.getUuid());
		
		Assert.assertNull(getService().read(entity.getUuid()));
		Assert.assertEquals(0, getService().findAllCount().intValue());
		
	}
}
