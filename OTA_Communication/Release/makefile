################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -f

# All of the sources participating in the build are defined here
-include sources.mk
-include subdir.mk
-include libraries/Wire/subdir.mk
-include libraries/WiFiManager/subdir.mk
-include libraries/SPI/subdir.mk
-include libraries/PubSubClientTools/subdir.mk
-include libraries/PubSubClient/src/subdir.mk
-include libraries/MQTT/src/lwmqtt/subdir.mk
-include libraries/MQTT/src/subdir.mk
-include libraries/ESP8266WiFi/src/subdir.mk
-include libraries/ESP8266WebServer/src/detail/subdir.mk
-include libraries/DNSServer/src/subdir.mk
-include libraries/DFRobot_CCS811/subdir.mk
-include core/core/umm_malloc/subdir.mk
-include core/core/spiffs/subdir.mk
-include core/core/libb64/subdir.mk
-include core/core/subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(INO_DEPS)),)
-include $(INO_DEPS)
endif
ifneq ($(strip $(PDE_DEPS)),)
-include $(PDE_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
AR += \
/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/arduino.ar \

ELF += \
OTA_Testing.elf \

HEX += \
OTA_Testing.hex \


# All Target
all: OTA_Testing

# Tool invocations
OTA_Testing: $(HEX)
	@echo 'Building target: $@'
	@echo 'Printing size:'
	"/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/tools/xtensa-lx106-elf-gcc/3.0.4-gcc10.3-1757bed/bin/xtensa-lx106-elf-size" -A "/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/OTA_Testing.elf"
	@echo 'Finished building target: $@'
	@echo ' '

/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/arduino.ar: $(AR_OBJ)
	@echo 'Starting archiver'
	"/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/tools/xtensa-lx106-elf-gcc/3.0.4-gcc10.3-1757bed/bin/xtensa-lx106-elf-ar" cru     /home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/arduino.ar $(AR_OBJ)
	@echo 'Finished building: $@'
	@echo ' '

OTA_Testing.elf: $(LINK_OBJ) $(AR)
	@echo 'Starting combiner'
	${recipe.hooks.linking.prelink.1.pattern}
	${recipe.hooks.linking.prelink.2.pattern}
	${recipe.hooks.linking.prelink.3.pattern}
	${recipe.hooks.linking.prelink.4.pattern}
	"/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/tools/xtensa-lx106-elf-gcc/3.0.4-gcc10.3-1757bed/bin/xtensa-lx106-elf-gcc" -fno-exceptions -Wl,-Map "-Wl,/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/OTA_Testing.map" -g -w -Werror=return-type -Os -nostdlib -Wl,--no-check-sections -u app_entry -u _printf_float -u _scanf_float -Wl,-static "-L/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/hardware/esp8266/3.0.2/tools/sdk/lib" "-L/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/hardware/esp8266/3.0.2/tools/sdk/lib/NONOSDK22x_190703" "-L/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release" "-L/home/danielyeste/Desktop/Sloeber/arduinoPlugin/packages/esp8266/hardware/esp8266/3.0.2/tools/sdk/libc/xtensa-lx106-elf/lib" "-Tlocal.eagle.flash.ld" -Wl,--gc-sections -Wl,-wrap,system_restart_local -Wl,-wrap,spi_flash_read  -o "/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/OTA_Testing.elf" -Wl,--start-group   $(LINK_OBJ)   /home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release/arduino.ar  -lhal -lphy -lpp -lnet80211 -llwip2-536-feat -lwpa -lcrypto -lmain -lwps -lbearssl -lespnow -lsmartconfig -lairkiss -lwpa2 -lstdc++ -lm -lc -lgcc -Wl,--end-group "-L/home/danielyeste/Documents/sloeber-workspace/OTA_Testing/Release"
	@echo 'Finished building: $@'
	@echo ' '

OTA_Testing.hex: $(ELF)
	@echo 'Do all objcopy commands'
	${recipe.objcopy.hex.1.pattern}
	${recipe.objcopy.hex.2.pattern}
	${recipe.objcopy.hex.3.pattern}
	${recipe.objcopy.hex.pattern}
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(C_UPPER_DEPS)$(PDE_DEPS)$(C_DEPS)$(AR)$(CC_DEPS)$(AR_OBJ)$(C++_DEPS)$(LINK_OBJ)$(CXX_DEPS)$(ASM_DEPS)$(HEX)$(INO_DEPS)$(SIZEDUMMY)$(S_UPPER_DEPS)$(ELF)$(CPP_DEPS)
	-@echo ' '

.PHONY: all clean dependents

-include ../makefile.targets
