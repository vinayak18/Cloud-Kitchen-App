const productserviceUrl = '/productserviceurl/api/v1/productservice';
const userserviceUrl = '/userserviceurl/api/v1/userservice';
const feedbackserviceUrl = '/feedbackserviceurl/api/v1/feedbackservice';
const messageserviceUrl = '/messageserviceurl/api/v1/messageservice';

export const urls = {
  userUrls: {
    login: `${userserviceUrl}/auth/user/login`,
    socialFbLogin: `${userserviceUrl}/auth/facebook/login/{id}`,
    socialGoogleLogin: `${userserviceUrl}/auth/google/login`,
    register: `${userserviceUrl}/auth/user/register`,
    byEmail: `${userserviceUrl}/verify/user/email/{email}`,
    byId: `${userserviceUrl}/verify/user/id/{id}`,
    uploadImage: `${userserviceUrl}/verify/user/upload/image/{userId}`,
    updateUser: `${userserviceUrl}/verify/update/user`,
    getImage: `${userserviceUrl}/verify/user/get/image`,
    addToCart: `${userserviceUrl}/verify/user/update/cart/{email}`,
    initiateStripePayment: `${userserviceUrl}/verify/order/stripe/initialize`,
    initiateRazorPayPayment: `${userserviceUrl}/verify/order/razorpay/initialize`,
    byOrderId: `${userserviceUrl}/verify/order/retrieve/{id}`,
    activeOrders: `${userserviceUrl}/verify/order/{userId}/active`,
    pastOrders: `${userserviceUrl}/verify/order/{userId}/past`,
    allOrdersByStatus: `${userserviceUrl}/verify/order/{status}`,
    updateOrderRating: `${userserviceUrl}/verify/order/{id}/update/rating/{rating}`,
    updateOrderStatus: `${userserviceUrl}/verify/order/{id}/update/status/{status}`,
    addNewOrder: `${userserviceUrl}/verify/order/add`,
    calculateDistance: `${userserviceUrl}/verify/order/calculate/distance`,
    resetPassword: `${userserviceUrl}/public/user/reset/password`,
    allCustomers: `${userserviceUrl}/verify/customer/all`,
  },
  productUrls: {
    all: `${productserviceUrl}/product/all`,
    byId: `${productserviceUrl}/product/{pid}`,
    byFoodType: `${productserviceUrl}/product/type/{foodtype}`,
    bestseller: `${productserviceUrl}/product/bestseller/{pid}`,
    // add: `${productserviceUrl}/verify/product/add`,
    update: `${productserviceUrl}/verify/product/update`,
    delete: `${productserviceUrl}/verify/product/delete/{pid}`,
    changeLiveStatus: `${productserviceUrl}/verify/product/update/status/{pid}`,
  },
  reviewUrls: {
    byId: `${productserviceUrl}/verify/user/{pid}/review`,
    add: `${productserviceUrl}/verify/user/{pid}/review/add`,
    update: `${productserviceUrl}/verify/user/{pid}/review/update`,
    delete: `${productserviceUrl}/verify/user/{pid}/review/delete`,
  },
  couponUrls: {
    byCode: `${userserviceUrl}/verify/user/coupon/retrieve`,
  },
  feedbackUrls: {
    add: `${feedbackserviceUrl}/feedback/add`,
    testimony: `${feedbackserviceUrl}/customer/testimony`,
  },
  messageUrls: {
    forget: `${messageserviceUrl}/auth/user/forgetpassword`,
    validate: `${messageserviceUrl}/auth/user/validate/{email}/otp/{otp}`,
  },
};
