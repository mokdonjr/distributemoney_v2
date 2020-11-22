package com.seungchan.distributemoney_v2.common.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;

public abstract class AbsObjectPersistenceService implements IObjectPersistenceService {

    @Override
    public IObjectData getObjectDefault(IObjectKey key) {
        // TODO : 캐싱
        return selectData(key);
    }

    @Override
    public IObjectData createObjectDefault(IParamData param) throws BusinessException {
        IObjectData created = beforeCreateObject(param);
        created = insertData(created);
        // TODO : 컴포넌트 처리 (컴포넌트 설계 필요)
        // TODO : 캐시

        afterCreateObject(param, created);
        return created;
    }

    /**
     * 오브젝트 생성 전처리
     * @param param
     */
    protected IObjectData beforeCreateObject(IParamData param) throws BusinessException {
        checkCreateObject(param);
        return createObjectInstanceDefault(param);
    }

    /**
     * 오브젝트 생성 전 예외 처리
     * @param param
     */
    protected abstract void checkCreateObject(IParamData param) throws BusinessException;

    /**
     * 오브젝트 생성 후처리
     * @param param
     */
    protected void afterCreateObject(IParamData param, IObjectData object) throws BusinessException {

    }

    @Override
    public void updateObjectDefault(IObjectData object) throws BusinessException {
        beforeUpdateObject(object);
        IObjectData updated = updateObjectInstanceDefault(object);
        updateData(updated);
        // TODO : 컴포넌트 처리 (컴포넌트 설계 필요)
        // TODO : 캐시 갱신

        afterUpdateObject(object);
    }

    /**
     * 오브젝트 갱신 전처리
     * @param object
     */
    protected IObjectData beforeUpdateObject(IObjectData object) throws BusinessException {
        var beforeObject = getObjectDefault(object.getObjectKey());
        if (beforeObject == null)
            throw new BusinessException("존재하지 않는 오브젝트를 갱신할 수 없습니다.");
        checkUpdateObject(object, beforeObject);
        return updateObjectInstanceDefault(object);
    }

    /**
     * 오브젝트 갱신 전 예외처리
     * @param object
     * @param before
     * @throws BusinessException
     */
    protected abstract void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException;

    /**
     * 오브젝트 갱신 후처리
     * @param object
     */
    protected void afterUpdateObject(IObjectData object) {

    }

    @Override
    public void deleteObjectDefault(IObjectKey key) {
        beforeDeleteObject(key);
        deleteData(key);
        // TODO : 컴포넌트 처리 (컴포넌트 설계 필요)
        // TODO : 캐시 삭제

        afterDeleteObject(key);
    }

    /**
     * 오브젝트 제거 전처리
     * @param key
     */
    protected void beforeDeleteObject(IObjectKey key) {

    }

    /**
     * 오브젝트 제거 후처리
     * @param key
     */
    protected void afterDeleteObject(IObjectKey key) {

    }
}
