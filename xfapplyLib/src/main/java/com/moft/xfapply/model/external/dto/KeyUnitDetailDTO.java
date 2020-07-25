package com.moft.xfapply.model.external.dto;

import java.util.List;
import java.util.Map;

/**
 * key_unit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class KeyUnitDetailDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8184561302467145632L;

    public KeyUnitDTO keyUnitDTO;

    /** 附件 */
    public List<KeyUnitMediaDTO> keyUnitMediaDTOs;

    /** 消防器材 */
    private List<Map<String, Object>> fireFacilityDTOs;

    /** 重点部位 */
    private List<Map<String, Object>> keyPartBaseDTOs;

    /** 分区 */
    private List<Map<String, Object>> partitionBaseDTOs;

    public KeyUnitDTO getKeyUnitDTO() {
        return keyUnitDTO;
    }

    public void setKeyUnitDTO(KeyUnitDTO keyUnitDTO) {
        this.keyUnitDTO = keyUnitDTO;
    }

    public List<KeyUnitMediaDTO> getKeyUnitMediaDTOs() {
        return keyUnitMediaDTOs;
    }

    public void setKeyUnitMediaDTOs(List<KeyUnitMediaDTO> keyUnitMediaDTOs) {
        this.keyUnitMediaDTOs = keyUnitMediaDTOs;
    }

    public List<Map<String, Object>> getFireFacilityDTOs() {
        return fireFacilityDTOs;
    }

    public void setFireFacilityDTOs(List<Map<String, Object>> fireFacilityDTOs) {
        this.fireFacilityDTOs = fireFacilityDTOs;
    }

    public List<Map<String, Object>> getKeyPartBaseDTOs() {
        return keyPartBaseDTOs;
    }

    public void setKeyPartBaseDTOs(List<Map<String, Object>> keyPartBaseDTOs) {
        this.keyPartBaseDTOs = keyPartBaseDTOs;
    }

    public List<Map<String, Object>> getPartitionBaseDTOs() {
        return partitionBaseDTOs;
    }

    public void setPartitionBaseDTOs(List<Map<String, Object>> partitionBaseDTOs) {
        this.partitionBaseDTOs = partitionBaseDTOs;
    }

}