package com.example.teamprojectsolocode.schedules

// 더 구체적인 할 일 클래스
data class ScheduleInfo(var todo: String,
                        var date: String,
                        var hour: Int,
                        var minu: Int,
                        var ampm: String,
                        var time: String,
                        var dday: String)