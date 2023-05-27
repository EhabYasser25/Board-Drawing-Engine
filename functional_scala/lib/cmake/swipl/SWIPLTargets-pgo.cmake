#----------------------------------------------------------------
# Generated CMake target import file for configuration "PGO".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "swipl::swipl" for configuration "PGO"
set_property(TARGET swipl::swipl APPEND PROPERTY IMPORTED_CONFIGURATIONS PGO)
set_target_properties(swipl::swipl PROPERTIES
  IMPORTED_LOCATION_PGO "${_IMPORT_PREFIX}/./bin/swipl.exe"
  )

list(APPEND _IMPORT_CHECK_TARGETS swipl::swipl )
list(APPEND _IMPORT_CHECK_FILES_FOR_swipl::swipl "${_IMPORT_PREFIX}/./bin/swipl.exe" )

# Import target "swipl::libswipl" for configuration "PGO"
set_property(TARGET swipl::libswipl APPEND PROPERTY IMPORTED_CONFIGURATIONS PGO)
set_target_properties(swipl::libswipl PROPERTIES
  IMPORTED_IMPLIB_PGO "${_IMPORT_PREFIX}/./bin/libswipl${CMAKE_IMPORT_LIBRARY_SUFFIX}"
  IMPORTED_LOCATION_PGO "${_IMPORT_PREFIX}/./bin/libswipl.dll"
  )

list(APPEND _IMPORT_CHECK_TARGETS swipl::libswipl )
list(APPEND _IMPORT_CHECK_FILES_FOR_swipl::libswipl "${_IMPORT_PREFIX}/./bin/libswipl${CMAKE_IMPORT_LIBRARY_SUFFIX}" "${_IMPORT_PREFIX}/./bin/libswipl.dll" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
