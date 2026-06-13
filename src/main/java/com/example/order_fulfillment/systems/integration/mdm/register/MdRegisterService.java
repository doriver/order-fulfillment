package com.example.order_fulfillment.systems.integration.mdm.register;

import com.example.order_fulfillment.systems.integration.mdm.register.dto.ChannelRegisterDTO;
import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.channel.domain.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    master data 등록
 */
@Service
@RequiredArgsConstructor
public class MdRegisterService {

    private final ChannelRepository channelRepository;

    @Transactional
    public Long saveChannel(ChannelRegisterDTO dto) {
        Channel channel = Channel.create(dto.code(), dto.name());
        return channelRepository.save(channel).getId();
    }
}
