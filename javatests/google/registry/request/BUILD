package(
    default_testonly = 1,
    default_visibility = ["//java/google/registry:registry_project"],
)

licenses(["notice"])  # Apache 2.0

load("//java/com/google/testing/builddefs:GenTestRules.bzl", "GenTestRules")

java_library(
    name = "request",
    srcs = glob(["*.java"]),
    deps = [
        "//java/google/registry/request",
        "//java/google/registry/request/auth",
        "//java/google/registry/security",
        "//javatests/google/registry/testing",
        "@com_google_appengine_api_1_0_sdk//:testonly",
        "@com_google_guava",
        "@com_google_guava_testlib",
        "@com_google_truth",
        "@com_googlecode_json_simple",
        "@javax_inject",
        "@javax_servlet_api",
        "@joda_time",
        "@junit",
        "@org_mockito_all",
    ],
)

GenTestRules(
    name = "GeneratedTestRules",
    test_files = glob(["*Test.java"]),
    deps = [":request"],
)
