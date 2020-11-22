package com.seungchan.distributemoney_v2.user.service;

import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import com.seungchan.distributemoney_v2.common.data.IObjectData;
import com.seungchan.distributemoney_v2.common.data.key.IObjectKey;
import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.param.IParamData;
import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceService;
import com.seungchan.distributemoney_v2.common.util.NullUtil;
import com.seungchan.distributemoney_v2.common.util.RandomUtil;
import com.seungchan.distributemoney_v2.user.data.ObjectDataUser;
import com.seungchan.distributemoney_v2.user.data.key.ObjectKeyUser;
import com.seungchan.distributemoney_v2.user.mapper.ObjectDataUserMapper;
import com.seungchan.distributemoney_v2.user.param.ParamUser;
import com.seungchan.distributemoney_v2.userAccount.param.ParamUserAccount;
import com.seungchan.distributemoney_v2.userAccount.service.ObjectServiceUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 계정 관리 서비스
 */
@Service
public class ObjectServiceUser extends AbsObjectPersistenceService {

    @Autowired
    private ObjectDataUserMapper objectDataUserMapper;
    @Autowired
    private ObjectServiceUserAccount objectServiceUserAccount;

    @Override
    public ObjectDataUser selectData(IObjectKey objectKey) {
        ObjectKeyUser key = (ObjectKeyUser) objectKey;
        return objectDataUserMapper.selectObjectDataUser(key.getUserId());
    }

    public ObjectDataUser selectData(String userId) {
        ObjectKeyUser key = new ObjectKeyUser();
        key.setUserId(userId);
        return selectData(key);
    }

    @Override
    public ObjectDataUser selectDataWithLock(IObjectKey objectKey) {
        ObjectKeyUser key = (ObjectKeyUser) objectKey;
        return objectDataUserMapper.selectObjectDataUserForUpdate(key.getUserId());
    }

    public ObjectDataUser selectDataWithLock(String userId) {
        ObjectKeyUser key = new ObjectKeyUser();
        key.setUserId(userId);
        return selectDataWithLock(key);
    }

    @Override
    public ObjectDataUser insertData(IObjectData objectData) {
        ObjectDataUser data = (ObjectDataUser) objectData;
        objectDataUserMapper.insertObjectDataUser(data);
        return data;
    }

    @Override
    public void updateData(IObjectData objectData) {
        ObjectDataUser data = (ObjectDataUser) objectData;
        objectDataUserMapper.updateObjectDataUser(data);
    }

    @Override
    public void deleteData(IObjectKey objectKey) {
        ObjectKeyUser key = (ObjectKeyUser) objectKey;
        objectDataUserMapper.deleteObjectDataUser(key.getUserId());
    }

    public void deleteData(String userId) {
        ObjectKeyUser key = new ObjectKeyUser();
        key.setUserId(userId);
        deleteData(key);
    }

    @Override
    protected void checkCreateObject(IParamData param) throws BusinessException {
        ParamUser paramUser = (ParamUser) param;

        // TODO : userId 유효한지 체크
        if (paramUser.getUserId() == null)
            throw new BusinessException("UserId 를 입력해주세요");

        if (paramUser.getUserName() == null)
            paramUser.setUserName("DEFAULT_USER_NAME");

    }

    @Override
    public ObjectDataUser createObjectInstanceDefault(IParamData param) {
        ParamUser paramUser = (ParamUser) param;
        var data = new ObjectDataUser();
        data.setUserId(paramUser.getUserId());
        data.setUserName(paramUser.getUserName());
        data.setBalance(NullUtil.getOrDefault(paramUser.getBalance()));
        return data;
    }

    @Override
    protected void afterCreateObject(IParamData param, IObjectData object) throws BusinessException {
        ObjectDataUser objectDataUser = (ObjectDataUser) object;

        // 회원가입시 카카오뱅크 계좌 자동 생성 이벤트~
        ParamUserAccount paramUserAccount = new ParamUserAccount();
        paramUserAccount.setBankType(BankType.KAKAO_BANK);
        // 12 자리 계좌번호 자동 발급 FIXME : guid 처리 필요
        paramUserAccount.setBankAccountNumber(String.valueOf(RandomUtil.between(100000000000L, 999999999999L)));
        paramUserAccount.setUserId(objectDataUser.getUserId());
        objectServiceUserAccount.createObjectDefault(paramUserAccount);
    }

    @Override
    protected void checkUpdateObject(IObjectData object, IObjectData before) throws BusinessException {
        ObjectDataUser beforeObjectDataUser = (ObjectDataUser) before;
        if (beforeObjectDataUser.getIsBlock())
            throw new BusinessException("정지된 페이머니 계정입니다");

        if (beforeObjectDataUser.getIsDelete())
            throw new BusinessException("존재하지 않거나 삭제된 페이머니 계정입니다");

        ObjectDataUser objectDataUser = (ObjectDataUser) object;
        if (objectDataUser.getBalance() == null)
            throw new BusinessException("페이머니의 잔액을 알 수 없어 갱신이 불가능합니다.");

        if (objectDataUser.getBalance() < 0)
            throw new BusinessException("페이머니에 잔액이 부족합니다");
    }
}
