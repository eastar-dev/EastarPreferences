@file:Suppress("SimplifyBooleanWithConstants")

package dev.eastar.sharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.security.SecureRandom

@DisplayName("SharedPreferences 테스트")
class SharePreferencesHelperTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            Pref.preferences = context.getSharedPreferences(SecureRandom.getInstanceStrong().nextInt().toString(), Context.MODE_PRIVATE)
            Pref.preferences.edit(true) { clear() }
        }
    }

    @DisplayName("값이 없을때 테스트")
    @Test
    fun testEmptyValue() {
        //@formatter:off
        assertTrue(PP.get<Boolean    > (Pref.TEST_EMPTY_BOOLEAN) == false             )
        assertTrue(PP.get<Int        > (Pref.TEST_EMPTY_INT    ) == 0                 )
        assertTrue(PP.get<Float      > (Pref.TEST_EMPTY_FLOAT  ) == 0F                )
        assertTrue(PP.get<Long       > (Pref.TEST_EMPTY_LONG   ) == 0L                )
        assertTrue(PP.get<String     > (Pref.TEST_EMPTY_STRING ) == ""                )
        assertTrue(PP.get<Set<String>> (Pref.TEST_EMPTY_SET    ) == emptySet<String>())
        //@formatter:on
    }

    @DisplayName("오퍼레이터를 이용한 값이 없을때 테스트")
    @Test
    fun testEmptyValueOperator() {
        //@formatter:off
        val emptyBoolean : Boolean     = PP[Pref.TEST_EMPTY_BOOLEAN]
        val emptyInt     : Int         = PP[Pref.TEST_EMPTY_INT    ]
        val emptyFloat   : Float       = PP[Pref.TEST_EMPTY_FLOAT  ]
        val emptyLong    : Long        = PP[Pref.TEST_EMPTY_LONG   ]
        val emptyString  : String      = PP[Pref.TEST_EMPTY_STRING ]
        val emptySet     : Set<String> = PP[Pref.TEST_EMPTY_SET    ]
        assertTrue(false               == emptyBoolean)
        assertTrue(0                   == emptyInt    )
        assertTrue(0F                  == emptyFloat  )
        assertTrue(0L                  == emptyLong   )
        assertTrue(""                  == emptyString )
        assertTrue(emptySet<String>()  == emptySet    )
        //@formatter:on

    }
    @DisplayName("오퍼레이터를 이용한 값이 없을때 직접 부르면 오류")
        @Test
        fun testEmptyValueOperatorError() {
        assertThrows(ClassNotFoundException::class.java) {
            true == PP[Pref.TEST_EMPTY_BOOLEAN]
        }
    }

    @DisplayName("값이 없을때 기본값 테스트")
    @Test
    fun testDefaultValue() {
        //@formatter:off
        assertTrue(PP[Pref.TEST_EMPTY_BOOLEAN, true                                            ] == true                                             )
        assertTrue(PP[Pref.TEST_EMPTY_INT    , 123                                             ] == 123                                              )
        assertTrue(PP[Pref.TEST_EMPTY_FLOAT  , 1.23F                                           ] == 1.23F                                            )
        assertTrue(PP[Pref.TEST_EMPTY_LONG   , Long.MAX_VALUE                                  ] == Long.MAX_VALUE                                   )
        assertTrue(PP[Pref.TEST_EMPTY_STRING , "enum define shared preferences"                ] == "enum define shared preferences"                 )
        assertTrue(PP[Pref.TEST_EMPTY_SET    , setOf("enum", "define", "shared", "preferences")] == setOf("enum", "define", "shared", "preferences") )
        //@formatter:on
    }

    @DisplayName("값 넣은 후 불러오기 테스트")
    @Test
    fun testPutAndGetValue() {
        //@formatter:off
        PP[Pref.TEST_BOOLEAN] = true
        PP[Pref.TEST_INT    ] = 123
        PP[Pref.TEST_FLOAT  ] = 1.23F
        PP[Pref.TEST_LONG   ] = Long.MAX_VALUE
        PP[Pref.TEST_STRING ] = "enum define shared preferences"
        PP[Pref.TEST_SET    ] = setOf("enum", "define", "shared", "preferences")

        assertTrue(true                                             == PP[Pref.TEST_BOOLEAN])
        assertTrue(123                                              == PP[Pref.TEST_INT    ])
        assertTrue(1.23F                                            == PP[Pref.TEST_FLOAT  ])
        assertTrue(Long.MAX_VALUE                                   == PP[Pref.TEST_LONG   ])
        assertTrue("enum define shared preferences"                 == PP[Pref.TEST_STRING ])
        assertTrue(setOf("enum", "define", "shared", "preferences") == PP[Pref.TEST_SET    ])


    }

    @DisplayName("값 넣은 후 기본값이 아니라 저장된 값이 불러오는지 테스트 ")
    @Test
    fun testPutAndGetValueNotDefault() {
        //@formatter:off
        PP[Pref.TEST_BOOLEAN] = true
        PP[Pref.TEST_INT    ] = 123
        PP[Pref.TEST_FLOAT  ] = 1.23F
        PP[Pref.TEST_LONG   ] = Long.MAX_VALUE
        PP[Pref.TEST_STRING ] = "enum define shared preferences"
        PP[Pref.TEST_SET    ] = setOf("enum", "define", "shared", "preferences")

        assertTrue(true                                             == PP[Pref.TEST_BOOLEAN, false                                                  ])
        assertTrue(123                                              == PP[Pref.TEST_INT    , -123                                                   ])
        assertTrue(1.23F                                            == PP[Pref.TEST_FLOAT  , -1.23F                                                 ])
        assertTrue(Long.MAX_VALUE                                   == PP[Pref.TEST_LONG   , Long.MIN_VALUE                                         ])
        assertTrue("enum define shared preferences"                 == PP[Pref.TEST_STRING , "not enum define shared preferences"                   ])
        assertTrue(setOf("enum", "define", "shared", "preferences") == PP[Pref.TEST_SET    , setOf("not","enum", "define", "shared", "preferences") ])
    }

    @Suppress("UNUSED_VARIABLE")
    @DisplayName("값 넣은 후 다른 타입으로 불러오기 오류 테스트")
    @Test
    fun testClassCastException() {
        assertThrows(ClassCastException::class.java) {
            PP[Pref.TEST_THROW_BOOLEAN2NUMBER] = true
            val result: Int = PP[Pref.TEST_THROW_BOOLEAN2NUMBER]
        }

        assertThrows(ClassCastException::class.java) {
            PP[Pref.TEST_THROW_INT2LONG] = 1
            val result: Long = PP[Pref.TEST_THROW_INT2LONG]
        }

        assertThrows(ClassCastException::class.java) {
            PP[Pref.TEST_THROW_INT2STRING] = 1
            val result: String = PP[Pref.TEST_THROW_INT2STRING]
        }
    }
}


