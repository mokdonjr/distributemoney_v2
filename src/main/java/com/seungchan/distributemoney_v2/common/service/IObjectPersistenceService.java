package com.seungchan.distributemoney_v2.common.service;

import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;

/**
 * 오브젝트 영속성 관리
 */
public interface IObjectPersistenceService {

    /**
     * CRUD 쿼리 수행
     * @param objectKey
     * @return
     */
    IObjectData selectData(IObjectKey objectKey);
    IObjectData selectDataWithLock(IObjectKey objectKey);
    IObjectData insertData(IObjectData objectData);
    void updateData(IObjectData objectData);
    void deleteData(IObjectKey objectKey);

    /**
     * 오브젝트 조회
     * @param key
     * @return
     */
    IObjectData getObjectDefault(IObjectKey key);

    /**
     * 오브젝트 인스턴스 생성
     * @param param
     * @return
     */
    IObjectData createObjectInstanceDefault(IParamData param);

    /**
     * 오브젝트 생성
     * @param param
     * @return
     */
    IObjectData createObjectDefault(IParamData param) throws BusinessException;

    /**
     * 오브젝트 인스턴스 메모리 갱신
     * @param object
     * @return
     */
    default IObjectData updateObjectInstanceDefault(IObjectData object) {
        // TODO : 미구현
        return object;
    }

    /**
     * 오브젝트 갱신
     * @param object
     */
    void updateObjectDefault(IObjectData object) throws BusinessException;

    /**
     * 오브젝트 제거
     * @param key
     */
    void deleteObjectDefault(IObjectKey key);
}
