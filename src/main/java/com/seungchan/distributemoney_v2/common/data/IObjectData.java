package com.seungchan.distributemoney_v2.common.data;

import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;

/**
 * 오브젝트 영속성 데이터
 */
public interface IObjectData {

    /**
     * 오브젝트 키 가져오기
     * @return
     */
    IObjectKey getObjectKey();

}
