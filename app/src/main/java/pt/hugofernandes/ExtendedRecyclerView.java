package pt.hugofernandes;

/*
 * Copyright 2015 Hugo Fernandes
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import pt.hugofernandes.extendedrecyclerview.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExtendedRecyclerView extends FrameLayout {

  private ProgressBar progressBar;
  private RecyclerView recyclerView;
  private SwipeRefreshLayout swipeRefreshLayout;
  private TextView emptyMessageView;
  private String emptyMessage;

  public ExtendedRecyclerView(Context context) {
    super(context);
    initView();
  }

  public ExtendedRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initAttrs(attrs);
    initView();
  }

  public ExtendedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(attrs);
    initView();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ExtendedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initAttrs(attrs);
    initView();
  }

  protected void initAttrs(AttributeSet attrs) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.awesomerecyclerview);
    try {
      emptyMessage = a.getString(R.styleable.awesomerecyclerview_empty_message);
      if (TextUtils.isEmpty(emptyMessage)) {
        emptyMessage = getContext().getString(R.string.empty_data);
      }
    } finally {
      a.recycle();
    }
  }

  private void initView() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_recycler_view, this);
    progressBar = (ProgressBar) view.findViewById(R.id.recycler_view_progress_bar);
    recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recycler_view_swipe_refresh);
    emptyMessageView = (TextView) view.findViewById(R.id.empty_message_view);
    emptyMessageView.setText(emptyMessage);
    swipeRefreshLayout.setEnabled(false);
  }

  public RecyclerView getRecyclerView() {
    return recyclerView;
  }

  /**
   * Remove the adapter from the recycler
   */
  public void clear() {
    recyclerView.setAdapter(null);
  }

  public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  public void setHasFixedSize(boolean hasFixedSize) {
    recyclerView.setHasFixedSize(hasFixedSize);
  }

  public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
    recyclerView.addOnScrollListener(listener);
  }

  public void setLayoutManager(RecyclerView.LayoutManager layout) {
    recyclerView.setLayoutManager(layout);
  }

  public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
    recyclerView.addOnItemTouchListener(listener);
  }

  public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
    recyclerView.removeOnItemTouchListener(listener);
  }

  public RecyclerView.Adapter getAdapter() {
    return recyclerView.getAdapter();
  }

  public void setOnTouchListener(OnTouchListener listener) {
    recyclerView.setOnTouchListener(listener);
  }

  public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
    recyclerView.addItemDecoration(itemDecoration);
  }

  public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
    recyclerView.addItemDecoration(itemDecoration, index);
  }

  public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
    recyclerView.removeItemDecoration(itemDecoration);
  }

  public void smoothScrollBy(int dx, int dy) {
    recyclerView.smoothScrollBy(dx, dy);
  }

  public void scrollTo(int x, int y) {
    recyclerView.scrollTo(x, y);
  }

  public void scrollBy(int x, int y) {
    recyclerView.scrollBy(x, y);
  }

  public void scrollToPosition(int position) {
    recyclerView.scrollToPosition(position);
  }

  public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
    swipeRefreshLayout.setEnabled(true);
    swipeRefreshLayout.setOnRefreshListener(listener);
  }

  public void setRefreshingColorResources(@ColorRes int colRes1, @ColorRes int colRes2, @ColorRes int colRes3,
      @ColorRes int colRes4) {
    swipeRefreshLayout.setColorSchemeResources(colRes1, colRes2, colRes3, colRes4);
  }

  public void setRefreshingColor(int col1, int col2, int col3, int col4) {
    swipeRefreshLayout.setColorSchemeColors(col1, col2, col3, col4);
  }

  public void setRefreshEnabled(boolean enabled) {
    swipeRefreshLayout.setEnabled(enabled);
  }

  public void setRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.setRefreshing(isRefreshing);
  }

  public void setAdapter(RecyclerView.Adapter adapter) {
    setAdapterInternal(adapter);
  }

  private void setAdapterInternal(RecyclerView.Adapter adapter) {
    swipeRefreshLayout.setRefreshing(false);

    if (adapter == null) {
      recyclerView.setVisibility(View.INVISIBLE);
      emptyMessageView.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
      return;
    }

    recyclerView.setAdapter(adapter);
    progressBar.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        update();
      }

      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        update();
      }

      @Override
      public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        update();
      }

      @Override
      public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        update();
      }

      @Override
      public void onChanged() {
        super.onChanged();
        update();
      }

      private void update() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if (recyclerView.getAdapter().getItemCount() == 0) {
          emptyMessageView.setVisibility(View.VISIBLE);
        } else {
          recyclerView.setVisibility(View.VISIBLE);
          emptyMessageView.setVisibility(View.GONE);
        }
      }
    });

    emptyMessageView.setVisibility(adapter != null && adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
  }

}
